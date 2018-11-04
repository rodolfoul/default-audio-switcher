import com.sun.jna.Pointer
import com.sun.jna.platform.win32.COM.COMUtils
import com.sun.jna.platform.win32.COM.Unknown
import com.sun.jna.platform.win32.WinNT
import java.io.Closeable

abstract class ComUnknown : Unknown, Closeable {

	constructor() : super()
	constructor(pointer: Pointer) : super(pointer)

	fun comCall(vTableId: Int, values: Array<*>) {
		COMUtils.checkRC(_invokeNativeObject(vTableId, arrayOf(pointer, *values), WinNT.HRESULT::class.java) as WinNT.HRESULT)
	}

	override fun close() {
		Release()
	}
}