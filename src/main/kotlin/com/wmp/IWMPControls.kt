package com.wmp

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod

@ComInterface(iid = "74C09E02-F828-11d2-A74B-00A0C905F36E")
interface IWMPControls {

	@ComMethod
	fun play()
}