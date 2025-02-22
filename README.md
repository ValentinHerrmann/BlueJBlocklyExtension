Developed and tested for Windows only!

## Getting Started
1. Ensure you have BlueJ installed on your machine and it's not running.
2. Download the Release and launch the MSI-Installer inside the ZIP. The default path is correct if you have BlueJ installed at it's default path. This will install BBE with all dependencies.
3. Launch BlueJ and you are ready to go. At Help -> Installed Extensions you should see BBE listed.
4. Right-Click a class and see the Option Blockly at the bottom of the context menu. First launch of Blockly might take 1-3 minutes. If after that time you only see a blank window restart BlueJ. If the problem still exists please submit an issue [here](https://github.com/users/ValentinHerrmann/projects/1).

## Configuration
In your BlueJ folder at `{{BlueJFolder}}/lib/Blockly/BBE.ini` the global configuration file is located. It can be overwritten (all entries!) by a configuration file at `{{ProjectFolder}}/Blockly/BBE.ini`.

The default config is:
``` ini
[Logging]

; If set to true a log file {{ProjectFolder}}/logs/BBE.log is created and used.
; Not recommended on global level as no file size guard or similar is available!
active=false    


[Paths]

; Relative path where to look for Blockly2Java HTML file.
; Tries to find {{ProjectFolder}}/{{blocklyHtmlPath}} first
; and if not successful uses {{BlueJFolder}}/lib/{{blocklyHtmlPath}}
blocklyHtmlPath=/Blockly/index.html

; Absolute Path to where your jcef-bundle is located.
; Users should have write access in the Directory.
; BBE_Installer installs jcef-bundle to Public, System32 and {{BlueJFolder}}
jcefBundlePath=C:/Users/Public/jcef-bundle/
```

You can lock classes for use with Blockly (as Blockly automatically overwrites all contents as soon as launched):
`{{ProjectFolder}}/Blockly/noBlocklyClasses.config` can contain one Regex per line to lock classes for use with Blockly.
 
