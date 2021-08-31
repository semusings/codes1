# import dependencies
import io
import PyPDF2
import os
 
from gtts import gTTS
from playsound import playsound

inputFile='paper.pdf'
outputFile='pdfGTTS.mp3'

# read pdf as string
pdf = PyPDF2.PdfFileReader(str(inputFile))
buf = io.StringIO()
for page in pdf.pages:
    buf.write(page.extractText())

# convert text to speech
pdfTxt=buf.getvalue()
language='en'
pdfGTTS=gTTS(text=pdfTxt,lang=language,slow=False)

# delete if file exits
if os.path.exists(outputFile):
  os.remove(outputFile)
else:
  print("The file does not exist")

# write mp3 file  
pdfGTTS.save(outputFile)

# play mp3
playsound(outputFile)
