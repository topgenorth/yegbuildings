#!/usr/bin/ruby

for txtfile in Dir.glob("*.txt")
	File.delete(txtfile)
end

for pdffile in Dir.glob("*.pdf")
	system("pdftotext", pdffile)
end
