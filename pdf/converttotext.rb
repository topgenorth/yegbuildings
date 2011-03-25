#!/usr/bin/ruby
require 'fileutils'

Dir.glob("*.txt") do |file| File.delete(file) end
Dir.glob("*.jpg") do |file| File.delete(file) end

Dir.glob("*.pdf") do |pdf_file|
	basename = File.basename(pdf_file,'.*')
	if (File.directory?(basename))
		Dir.glob("./#{basename}/*.*") do |file| File.delete(file) end
	else
		Dir.mkdir basename unless File.directory?(basename)
	end
	# TODO: strip out '' (apostrophes)
	htmlfile = "./#{basename}/#{basename}.html"
	asset_htmlfile = "../java/assets/html/#{basename}.html"
	system("pdftotext", "-htmlmeta", pdf_file, htmlfile)
	system("pdfimages", "-j",  pdf_file, "./#{basename}/")
	FileUtils.cp htmlfile, asset_htmlfile

	puts "Converted #{pdf_file} to text and extracted images to #{basename}, copied file to #{asset_htmlfile}."
end

