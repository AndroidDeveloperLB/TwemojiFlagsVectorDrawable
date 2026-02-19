## Twemoji Flags VectorDrawable 
This Android library was created due to lack of up-to-date and easy to download TTF files of Twemoji font, that I wanted to use for its flags emojis.
It allows to use the flags emojis from there on your Android apps with ease.

## Background
I wanted to have some way to use the flags of this font, as I didn't like the ones of Google that are "wavy", and other fonts seem to have some problematic licenses too. A nice bonus was also the tiny size of it, at least on the first TTF file that I've found.

Best I've found for this is the old [twemoji-colr](https://github.com/mozilla/twemoji-colr) and some automatically built font ([here](https://mirror.whynothugo.nl/twemoji.ttf/)), both are outdated and have some weird [digits issue](https://github.com/mozilla/twemoji-colr/issues/56) even though they should handle only emojis anyway. They also don't offer support for both new and old Android versions, as up to API 28 Android can handle only raster font properly, and only from API 29 it can handle vector font properly.

So, what I did is to import the flags-emoji SVG files from the official repository, optimize  (using [this website](https://devina.io/svg-minifier)) , convert them to VectorDrawable of Android using Android Studio, optimize again (using [this plugin](https://github.com/Miha-x64/Mikes_IDEA_extensions)), and replace each flag emoji in the text during runtime using a helper class. 

## How to use the library
Dependency can be added using Jitpack:
https://jitpack.io/#AndroidDeveloperLB/TwemojiFlagsVectorDrawable

Then, either use the `TwemojiFlagUtils.process` function to convert the text to one that will show the emoji flags, or use one of the common classes I've made that do it for you: 
- TwemojiFlagTextView
- TwemojiFlagButton
- TwemojiFlagEditText
- TwemojiFlagTextInputEditText

You can also check their code in case you want to create something similar.

## How to get up-to-date with newest flags emojis
Here's how to get newest flags, in case this repository seems outdated compared to what's on Twemoji repository:
1. First, as you probably want to monitor the Twemoji repository, and sadly it hasn't had any new releases published for a long time, you can monitor its commits instead. You can use RSS to do it, by monitoring this into your favorite RSS tool: https://github.com/twitter/twemoji/commits/master.atom
2. Once you've found some updates to the Twemoji repository, copy the content of the svg folder of the Twemoji repository into the `extras\font svg to work on` folder of this repository.
3. Run the batch file `filter to have just flags emojis.bat`. This batch file should copy (unless there is some bug in it) all flags emojis of the current folder into `Twemoji_Android_Flags` folder, overriding existing files there.
4. Delete the SVG files you've copied. No need for them anymore
5. Check if the folder of `Twemoji_Android_Flags` has changed. If so, some flags were updated. You can optimize them using the tools I've mentioned [above](https://github.com/AndroidDeveloperLB/TwemojiFlagsVectorDrawable?tab=readme-ov-file#background) as you put them into the `library\src\main\res-flags\drawable` as VectorDrawable files. Note that sometimes the optimizations could cause issues, so make sure you see them well after having them applied.

## Known issues
I'm not sure if this issue exists on real devices, but on Android emulator API 23 , it doesn't show well the Iranian flag (the one with the lion). On API 24 it seems to be shown fine.

## What about a font file instead?
As mentioned above, I failed to find how to generate or fetch the latest one. 
If you find how to do it easily on Windows OS, please let me know.
There is even a python command that I've found that could make a subset-font file that has only flag emojis out of the given TTF file:

    python -m fontTools.subset Twemoji.ttf --unicodes="U+0000,U+1F1E6-1F1FF" --layout-features="*" --glyph-names --output-file=Twemoji_flags.ttf

## License

The library has its own license that I've set, but the Twemoji has its [own licenses](https://github.com/twitter/twemoji?tab=readme-ov-file#license). 
