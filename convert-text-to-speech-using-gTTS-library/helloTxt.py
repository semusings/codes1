# import dependencies
import os

from gtts import gTTS
from playsound import playsound

# convert text to speech
helloTxt="Hello Everyone! This is Bhuwan Prasad Upadhyay! Welcome to Convert Text To Speech using GTTS Library"
language='en'
helloGTTS=gTTS(text=helloTxt,lang=language,slow=False)
if os.path.exists("helloGTTS.mp3"):
  os.remove("helloGTTS.mp3")
else:
  print("The file does not exist")

# write mp3 file  
helloGTTS.save("helloGTTS.mp3")

# play mp3
playsound("helloGTTS.mp3")