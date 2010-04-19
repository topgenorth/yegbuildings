require 'rubygems'
require 'rake'
require 'rexml/document'
require 'rake/clean'

def manifest
  @parsed ||= begin
    doc = REXML::Document.new(File.read('AndroidManifest.xml'))
    {
      :package => doc.root.attribute('package').to_s
    }
  end
end

app_pkg = manifest[:package]
package_keystore = "../KeyStores/historicalbuildings-release-key.keystore"
package_keystore_alias = "historicalbuildings-release"
project = app_pkg.gsub(/\./, '_')

sdk_location = ENV['ANDROID_SDK'] || '/home/tom/opt/android-sdk-linux'
jsdk_location = ENV['JAVA_SDK'] || '/usr/lib/jvm/java-6-sun'
android_platform = "android-1.6"

src = 'src'
gen = 'gen'
res = 'res'
bin = 'bin'
libs = 'libs'
assets = 'assets'
classes = "#{bin}/classes"
ap_ = "#{bin}/#{project}.ap_"
apk = "#{bin}/#{project}.apk"


# android stuff
google_maps_api_location = "#{sdk_location}/add-ons/google_apis-4_r02/libs/maps.jar"
android_platform_location = "#{sdk_location}/platforms/#{android_platform}"
android_jar = "#{android_platform_location}/android.jar"
android_aidl = "#{android_platform_location}/framework.aidl"
android_aapt = "#{android_platform_location}/tools/aapt"
android_dex = "#{android_platform_location}/tools/dx"
android_apkbuilder = "#{sdk_location}/tools/apkbuilder"

intermediate_dex_location = "#{bin}/classes.dex"

directory gen
directory bin
directory classes
dirs = [gen, bin, classes]

CLEAN.include(gen, bin, 'out')
CLASSPATH = FileList["#{libs}/**/*.jar", "#{google_maps_api_location}" ]
BOOTCLASSPATH = FileList[android_jar]

# Extensions for standard rake classes.
module Rake
  class FileList
    def to_cp(sep = File::PATH_SEPARATOR)
      self.join(sep)
    end
  end
end

def adb(*args)
  args.unshift '-s', ENV['DEVICE'] if ENV['DEVICE']
  sh "adb", *args
end

def compile(dest, *srcdirs)
  files = FileList.new
  srcdirs.each do |d|
    files.include("#{d}/**/*.java")
  end

  sh "javac", "-target", "1.6", "-g", "-bootclasspath", BOOTCLASSPATH.to_cp,  "-nowarn", "-Xlint:none",
     "-sourcepath", srcdirs.join(File::PATH_SEPARATOR), "-d", dest ,"-classpath", CLASSPATH.to_cp, *files
end

task :default => :release

task :resource_src => dirs do
  sh "#{android_aapt} package -m -J #{gen} -M AndroidManifest.xml -S #{res} -I #{android_jar}"
end

task :aidl => dirs do
  FileList["#{src}/**/*.aidl"].each do |f|
    sh "aidl -p #{android_aidl} -I #{src} -o #{gen} #{f}"
 end
end

task :update_debuggable_flag => dirs do
	puts "WE SHOULD SET android:debuggable TO FALSE"
end

task :compile => [:update_debuggable_flag, :resource_src, :aidl] do
  compile(classes, src, gen)
end

task :dex => :compile do
  sh "#{android_dex}", *["--dex", "--output=#{intermediate_dex_location}", classes ] + CLASSPATH
end

task :package_resources do
  opts = ["package", "-f", "-M", "AndroidManifest.xml", "-I", android_jar, "-S", res, "-F", ap_]
  opts += ["-A", assets] if File.directory?(assets)
  sh "#{android_aapt}", *opts
end

apkbuilder = Proc.new do |signed|
  args = [apk, "-f", intermediate_dex_location, "-rf", src, "-z", ap_]
  args += [ "-rj", libs] if File.directory?(libs)
  args += ["-u" ] unless signed

  sh "#{android_apkbuilder}", *args
end

desc "Builds the application and sign it with a release key."
task :release => [:dex, :package_resources] do
  apkbuilder.call(false)
  Rake::Task['package:sign'].invoke
end


desc "Installs the debug package onto a running emulator or device (DEVICE=<serialno>)."
task :install => :debug do
  adb 'install', apk
end

desc "Installs the debug package on a running emulator or device that already has the application (DEVICE=<serialno>)."
task :reinstall => :debug do
  adb 'install', '-r', apk
end

desc "uninstall the application from a running emulator or device (DEVICE=<serialno>)."
task :uninstall do
  adb 'uninstall',  app_pkg
end

namespace :package do
  desc "verify signature of the package."
  task :verify do
     sh "jarsigner",  "-verify",  "-certs", "-verbose", apk
  end

  desc "sign the package."
  task :sign do
    sh "jarsigner",  "-verbose",  "-keystore", package_keystore, apk, package_keystore_alias
  end
end
