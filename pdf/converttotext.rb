#!/usr/bin/ruby

Dir.glob("*.txt") do |file| File.delete(file) end
Dir.glob("*.jpg") do |file| File.delete(file) end

Dir.glob("*.pdf") do |file|
	basename = File.basename(file,'.*')
	if (File.directory?(basename))
		Dir.glob("./#{basename}/*.*") do |file2| File.delete(file2) end
	else
		Dir.mkdir basename unless File.directory?(basename)
	end
	system("pdftotext", "-htmlmeta", file, "./#{basename}/#{basename}.html")
	system("pdfimages", "-j",  file, "./#{basename}/")
#	File.copy file, "./#{basename}/file"
	puts "Converted #{file} to text and extracted images to #{basename}."
end

