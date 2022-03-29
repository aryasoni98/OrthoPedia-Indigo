package com.example.feetwings

import android.bluetooth.BluetoothGattCharacteristic

data class CharacteristicRead(
    override val characteristic: BluetoothGattCharacteristic
) : BleOperationType()

sealed class BleOperationType {
    abstract val characteristic: BluetoothGattCharacteristic
}