# import dependencies
import io
import PyPDF2
import os
 
from gtts import gTTS
from playsound import playsound

# read pdf as string
pdf = PyPDF2.PdfFileReader(str('paper.pdf'))
buf = io.StringIO()
for page in pdf.pages:
    buf.write(page.extractText())

# convert text to speech
pdfTxt=buf.getvalue()
language='en'
pdfGTTS=gTTS(text=pdfTxt,lang=language,slow=False)

# delete if file exits
if os.path.exists("pdfGTTS.mp3"):
  os.remove("pdfGTTS.mp3")
else:
  print("The file does not exist")

# write mp3 file  
pdfGTTS.save("pdfGTTS.mp3")

# play mp3
playsound("pdfGTTS.mp3")