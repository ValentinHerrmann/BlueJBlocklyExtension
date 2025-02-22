Developed and tested for Windows!

## Getting Started
1. Ensure you have BlueJ installed on your machine.
2. Download the Release and launch the MSI-Installer inside the ZIP. The default path is correct if you have BlueJ installed at it's default path. This will install BBE with all dependencies.
3. You are ready to go.

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
 
