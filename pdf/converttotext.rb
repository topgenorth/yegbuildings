#!/usr/bin/ruby

Dir.glob("*.txt") do |file| File.delete(file) end
Dir.glob("*.jpg") do |file| File.delete(file) end

Dir.glob("*.pdf") do |pdf_file|
	basename = File.basename(pdf_file,'.*')
	if (File.directory?(basename))
		Dir.glob("./#{basename}/*.*") do |file| File.delete(file) end
	else
		Dir.mkdir basename unless File.directory?(basename)
	end
	system("pdftotext", "-htmlmeta", pdf_file, "./#{basename}/#{basename}.html")
	system("pdfimages", "-j",  pdf_file, "./#{basename}/")
	Dir.glob("*.jpg").do |jpg_file| 
	end
	puts "Converted #{pdf_file} to text and extracted images to #{basename}."
end

