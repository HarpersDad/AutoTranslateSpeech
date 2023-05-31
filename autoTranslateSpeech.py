# imports
import os
import time
import pyaudio
import tkinter
from tkinter import *
from tkinter import ttk
from gtts import gTTS
from langdetect import detect
import speech_recognition as sr
from playsound import playsound
from googletrans import Translator

# list for the drop down menus
langList = ["English", "Spanish", "German", "Hindi", "French", "Japanese", "Russian", "Chinese"]

# speech recognizer for capturing speech from microphone
listener = sr.Recognizer()

# sets up the window
root = tkinter.Tk()
root.title("Voice Translation")
root.geometry("655x495")

# drop down variable for language to translate from
option_var = tkinter.StringVar(root)
option_var.set(langList[0])

# drop down variable for language to translate to
option_var2 = tkinter.StringVar(root)
option_var2.set(langList[1])

# create the combobox / drop down menu to translate from
menu = ttk.Combobox(root, textvariable=option_var)
menu["values"] = langList
menu.place(x=125, y=15)

# label for from drop down
label = tkinter.Label(root, text="Translate From: ")
label.place(x=30,y=15)

# create the combobox / drop down menu to translate to
menu2 = ttk.Combobox(root, textvariable=option_var2)
menu2["values"] = langList
menu2.place(x=125, y=60)

# label for to drop down
label2 = tkinter.Label(root, text="Translate To: ")
label2.place(x=30,y=60)

# creates text box for output that is disabled
textWin = tkinter.Text(root, wrap=tkinter.WORD, state="disabled")
textWin.place(x=5, y=100)

# function that shows the selected from-language in the text box, first by re-enabling the textbox, outputting the text, and re-disabling it
def display_output(event):
    textWin.configure(state="normal")
    selected_option = option_var.get()
    textWin.insert(tkinter.END, f"Translate from: {selected_option}\n")
    textWin.configure(state="disabled")

# function that shows the selected to-language in the text box, first by re-enabling the textbox, outputting the text, and re-disabling it
def display_output2(event):
    textWin.configure(state="normal")
    selected_option2 = option_var2.get()
    textWin.insert(tkinter.END, f"Translate to: {selected_option2}\n")
    textWin.configure(state="disabled")

# these two .bind lines show what language is selected for the drop down boxes and call the proper function
menu.bind("<<ComboboxSelected>>", display_output)
menu2.bind("<<ComboboxSelected>>", display_output2)

# match / switch statement that changes the language code for the from-language
def setFromLanguage():
    match menu.get():
        case "English":
            fromLanguage = "en"
        case "Spanish":
            fromLanguage = "es"
        case "German":
            fromLanguage = "de"
        case "French":
            fromLanguage = "fr"
        case "Japanese":
            fromLanguage = "ja"
        case "Russian":
            fromLanguage = "ru"
        case "Chinese":
            fromLanguage = "zh-tw"
        case "Hindi":
            fromLanguage = "hi"

    #print(menu.get())
    #print(fromLanguage)
    return fromLanguage

# match / switch statement that changes the language code for the to-language
def setToLanguage():
    match menu2.get():
        case "English":
            toLanguage = "en"
        case "Spanish":
            toLanguage = "es"
        case "German":
            toLanguage = "de"
        case "French":
            toLanguage = "fr"
        case "Japanese":
            toLanguage = "ja"
        case "Russian":
            toLanguage = "ru"
        case "Chinese":
            toLanguage = "zh-tw"
        case "Hindi":
            toLanguage = "hi"

    #print(menu2.get())
    #print(toLanguage)
    return toLanguage

# this function allows for the user to switch the to and from languages with each other to facilitate a conversation
def switchLanguage():
    # gets the appropriate language codes
    tLanguage = menu2.get()
    fLanguage = menu.get()

    # switches the language codes
    tempLanguage = fLanguage
    fLanguage = tLanguage
    tLanguage = tempLanguage

    # sets the language codes
    menu.set(fLanguage)
    menu2.set(tLanguage)

# function call that translates the speech that is recorded by the microphone
def translateSpeech():
    # sets languages
    fromLanguage = setFromLanguage()
    toLanguage = setToLanguage()

    # using the microphone, this listens for and captures and speech detected
    with sr.Microphone() as source:
        print("Now Listening.")
        audio = listener.listen(source)

    # try / catch that attemps to recognize the speech and set it as text
    try:
        recordedSpeech = listener.recognize_google(audio)

    # if the speech is unrecognized
    except sr.UnknownValueError:
        print("Unrecognized audio.  Could not understand.")

    # if there is an error
    except sr.RequestError as e:
        print("Could not get results; {0}".format(e))

    # sets up the imported Translator
    translator = Translator()

    # initializes the outputText variable
    outputText = ""

    # if / else statement that checks if recordedSpeech is empty or invalid
    if recordedSpeech != "" and recordedSpeech is not None:
        # translates speech
        translatedSpeech = translator.translate(recordedSpeech, src=fromLanguage, dest=toLanguage)

        # sets the translated speech as text
        outputText = translatedSpeech.text

        # this block returns the text box to a writable state, then outputs the translated text, then disables the textbox again
        textWin.configure(state="normal")
        textWin.insert(tkinter.END, "Translated text: " + outputText + "\n")
        textWin.configure(state="disabled")

        # sets up Google's text-to-speech import and turns the text into audio
        tts = gTTS(outputText)

        # deletes older mp3 file
        if os.path.exists("audio.mp3"):
            os.remove("audio.mp3")

        # saves the translated audio as an mp3 file
        tts.save("audio.mp3")

        # plays the translated audio using the OS's default mp3 player
        playsound("audio.mp3")

    # else
    else:
        # writes to the textbox that no speech was captured, and to try again.  <-- this probably doesn't work as intended
        textWin.configure(state="normal")
        textWin.insert(tkinter.END, "Speech could not be captured. Please try again." + "\n")
        textWin.configure(state="disabled")

# creates the Translate button
button = tkinter.Button(root, command=translateSpeech, text="Translate", height=1)
button.place(x=450, y=12)


# creates the Swap Languages button
button = tkinter.Button(root, command=switchLanguage, text="Swap Languages")
button.place(x=450, y=57)

# program loop
root.mainloop()
