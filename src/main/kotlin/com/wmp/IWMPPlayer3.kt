package com.wmp

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty

@ComInterface(iid = "54062B68-052A-4c25-A39F-8B63346511D4")
interface IWMPPlayer3 {
	@ComProperty
	fun setURL(url: String)

	@ComProperty
	fun getURL(): String

	@ComProperty
	fun getVersionInfo(): String

	@ComProperty
	fun getControls(): IWMPControls

	@ComProperty
	fun getEnabled(): Boolean

	@ComProperty
	fun setEnabled(v: Boolean)
}