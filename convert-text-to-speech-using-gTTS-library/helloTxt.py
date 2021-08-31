# import dependencies
import os

from gtts import gTTS
from playsound import playsound

outputFile='helloGTTS.mp3'

# convert text to speech
helloTxt="Hello Everyone! This is Bhuwan Prasad Upadhyay! Welcome to Convert Text To Speech using GTTS Library"
language='en'
helloGTTS=gTTS(text=helloTxt,lang=language,slow=False)
if os.path.exists(outputFile):
  os.remove(outputFile)
else:
  print("The file does not exist")

# write mp3 file  
helloGTTS.save(outputFile)

# play mp3
playsound(outputFile)
