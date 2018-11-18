package com.wmp

import com.sun.jna.platform.win32.COM.util.IUnknown
import com.sun.jna.platform.win32.COM.util.annotation.ComObject

@ComObject(progId = "WMPlayer.OCX.7")
interface WindowsMediaPlayer : IUnknown