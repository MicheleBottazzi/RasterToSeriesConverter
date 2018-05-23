#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed May 23 12:49:56 2018

@author: drugo
"""

# LEGGO I FILE   

import os
import subprocess
# Open a file
path = "/home/drugo/pyModis/scripts"
dirs = os.listdir( path )

# This would print all the files and directories
for files in dirs:
    fileHDFinput = files
    #print(fileHDFinput)
    fileTIFoutput = fileHDFinput.replace('.hdf', '.tif')
    #print(fileTIFoutput)
    first = 'modis_convert.py -s "( 0 1 )" -o '
    second =' -g 500 -e 32632 '    
    stringToLaunch =first+fileTIFoutput+second+fileHDFinput
    print(stringToLaunch)
    os.system(stringToLaunch)
    
