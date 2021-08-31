# import dependencies
import io
import PyPDF2
import os
 
from gtts import gTTS

# read pdf as string
pdf = PyPDF2.PdfFileReader(str('paper.pdf'))
buf = io.StringIO()
for page in pdf.pages:
    buf.write(page.extractText())

# convert text to speech
pdfTxt=buf.getvalue()
language='en'
pdfGTTS=gTTS(text=pdfTxt,lang=language,slow=False)
if os.path.exists("pdfGTTS.mp3"):
  os.remove("pdfGTTS.mp3")
else:
  print("The file does not exist")
pdfGTTS.save("pdfGTTS.mp3")