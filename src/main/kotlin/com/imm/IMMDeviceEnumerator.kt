package com.imm

import com.sun.jna.platform.win32.COM.COMUtils
import com.sun.jna.platform.win32.ObjBase
import com.sun.jna.platform.win32.Ole32
import com.sun.jna.platform.win32.Ole32Util
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.ptr.PointerByReference
import java.lang.IllegalStateException

//https://hackage.haskell.org/package/bindings-portaudio-0.1/src/portaudio/src/hostapi/wasapi/mingw-include/mmdeviceapi.h
class IMMDeviceEnumerator : ComUnknown() {

	init {
		val MMDeviceEnumeratorGuid = Ole32Util.getGUIDFromString("{BCDE0395-E52F-467C-8E3D-C4579291692E}")
		val IMMDeviceEnumeratorGuid = Ole32Util.getGUIDFromString("{A95664D2-9614-4F35-A746-DE8DB63617E6}")
		val enumeratorPointer = PointerByReference()
		COMUtils.checkRC(
			Ole32.INSTANCE.CoCreateInstance(
				MMDeviceEnumeratorGuid,
				null,
				ObjBase.CLSCTX_ALL,
				IMMDeviceEnumeratorGuid,
				enumeratorPointer
			)
		)

		pointer = enumeratorPointer.value
	}

	fun getAudioEndpoints(): List<AudioEndpoint> {
		EnumAudioEndpoints().use { immDeviceCollection ->

			val list = mutableListOf<AudioEndpoint>()
			for (i in 0 until immDeviceCollection.GetCount()) {
				immDeviceCollection.Item(i).use { item ->
					item.OpenPropertyStore(STGM.STGM_READ).use { propertyStore ->
						list.add(AudioEndpoint(item.GetId(), propertyStore.GetValue(PKEY_Device_FriendlyName)))
					}
				}
			}

			return list
		}
	}

	data class AudioEndpoint(val id: String, val name: String)

	/**
	 * HRESULT EnumAudioEndpoints(
	 *   cominterfaces.EDataFlow           dataFlow,
	 *   DWORD                             dwStateMask,
	 *   cominterfaces.IMMDeviceCollection **ppDevices
	 * );
	 */
	fun EnumAudioEndpoints(): IMMDeviceCollection {
		val result = PointerByReference()

		comCall(3, arrayOf(EDataFlow.eRender.ordinal, WinDef.DWORD(DeviceState.DEVICE_STATE_ACTIVE.value), result))

		return IMMDeviceCollection(result.value)
	}

	/*
	 * HRESULT GetDefaultAudioEndpoint(
	 *   EDataFlow dataFlow,
	 *   ERole     role,
	 *   IMMDevice **ppEndpoint
	 * );
	 */
	fun GetDefaultAudioEndpoint(): IMMDevice {
		val result = PointerByReference()

		comCall(
			4,
			arrayOf(
				EDataFlow.eRender.ordinal,
				WinDef.DWORD(DeviceState.DEVICE_STATE_ACTIVE.value),
				result
			)
		)

		return IMMDevice(result.value)
	}

	fun resolveAudioEndpoint(
		name: String
	): AudioEndpoint {
		val audioEndpoints = getAudioEndpoints()

		for (audioEndpoint in audioEndpoints) {
			if (audioEndpoint.name.contains(name, true)) {
				return audioEndpoint
			}
		}

		throw IllegalStateException("Could not find endpoint with name $name")
	}
}