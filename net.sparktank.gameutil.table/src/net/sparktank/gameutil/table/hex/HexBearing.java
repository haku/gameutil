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
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	// 6 arbitrary directions.
	B0( 1, -1),
	B1( 1,  0),
	B2( 0,  1),
	B3(-1,  1),
	B4(-1,  0),
	B5( 0, -1);
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final int dx;
	private final int dy;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	HexBearing (int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public int getDx () {
		return this.dx;
	}
	
	public int getDy () {
		return this.dy;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
