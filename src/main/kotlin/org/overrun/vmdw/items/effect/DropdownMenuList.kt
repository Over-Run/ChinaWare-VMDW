package org.overrun.vmdw.items.effect

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateMap

@Composable
fun DropdownMenuList(
    drop: MutableState<Boolean>,
    key: SnapshotStateMap<String, String>,
    map: MutableMap<String, String>,
    buttonText: MutableState<String?>,

    ) {
    dropDownMenuList(drop, key, map, buttonText)
}

@Composable
fun dropDownMenuList(
    drop: MutableState<Boolean>,
    key: SnapshotStateMap<String, String>,
    map: MutableMap<String, String>,
    buttonText: MutableState<String?>
) {
    DropdownMenu(
        expanded = drop.value,
        onDismissRequest = {
            drop.value = !drop.value
        }
    ) {
        for ((t, u) in map) {
            DropdownMenuItem(
                onClick = {
                    drop.value = false
                    key["language"] = t
                    buttonText.value = u
                }
            ) {
                Text(u)
            }
        }
    }
}
