/*
 * Copyright 2010 Fae Hutter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package net.sparktank.gameutil.table.hex;

/**
 * On a hex grid movement is constrained to one of 6 directions.
 * Since the name of these depends on which way up the board is,
 * they might as well have arbitrary names.
 */
public enum HexBearing {
	
	B0, B1, B2, B3, B4, B5 // 6 arbitrary directions.
	
}
