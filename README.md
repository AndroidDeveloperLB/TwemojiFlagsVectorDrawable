## Twemoji Flags VectorDrawable 
This library was created due to lack of up-to-date and easy to download TTF files of Twemoji font. 
I wanted to have some way to use the flags of this font, as I didn't like the ones of Google that are "wavy", and other fonts seem to have some problematic licenses too. A nice bonus was also the tiny size of it, at least on the first TTF file that I've found.

Best I've found for this is the old [twemoji-colr](https://github.com/mozilla/twemoji-colr) and some automatically built font ([here](https://mirror.whynothugo.nl/twemoji.ttf/)), both are outdated and have some weird [digits issue](https://github.com/mozilla/twemoji-colr/issues/56) even though they should handle only emojis anyway. They also don't offer support for both new and old Android versions, as up to API 28 Android can handle only raster font properly, and only from API 29 it can handle vector font properly.

So, what I did is to import the flags-emoji SVG files from the official repository, optimize  (using [this website](https://devina.io/svg-minifier)) , convert them to VectorDrawable of Android using Android Studio, optimize again (using [this plugin](https://github.com/Miha-x64/Mikes_IDEA_extensions)), and replace each flag emoji in the text during runtime using a helper class.

Here's how to get newest flags, in case this repository seems outdated compared to what's on Twemoji repository:
1. First, as you probably want to monitor the Twemoji repository, and sadly it hasn't had any new releases published for a long time, you can monitor its commits instead. You can use RSS to do it, by monitoring this into your favorite RSS tool: https://github.com/twitter/twemoji/commits/master.atom
2. Once you've found some updates to the Twemoji repository, copy the content of the svg folder of the Twemoji repository into the `extras\font svg to work on` folder of this repository.
3. Run the batch file `filter to have just flags emojis.bat`. This batch file should copy (unless there is some bug in it) all flags emojis of the current folder into `Twemoji_Android_Flags` folder, overriding existing files there.
4. Delete the SVG files you've copied. No need for them anymore
5. Check if the folder of `Twemoji_Android_Flags` has changed. If so, some flags were updated. You can optimize them using the tools I've mentioned above as you put them into the `library\src\main\res-flags\drawable` as VectorDrawable files.

