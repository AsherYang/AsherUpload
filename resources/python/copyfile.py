#!/usr/bin/python
# -*- coding:utf-8 -*-

from shutil import copyfile
import os

print 'copy file..'
def copy(srcPath, destPath):
    copyfile(srcPath, destPath)

def main():
    print 'please input src file path , and destPath'
    # srcPath = raw_input('srcPath : ')
    srcPath = '/Users/ouyangfan/Documents/1.txt'
    # destPath = raw_input('destPath : ')
    destPath = '/Users/ouyangfan/Documents/22.txt'
    if not os.path.isfile(srcPath):
        print 'src must a file'
        pass
        exit()
    copy(srcPath, destPath)

if __name__ == '__main__':
    main()
