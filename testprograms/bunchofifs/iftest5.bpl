var a : int;
var b : int;
var c : int;

procedure iftest() returns () ensures a == 9; modifies a; modifies b; modifies c; {
    b := 9;
    a := 0;
    c := 8;
    if(b == c) {
        a := b;
    } else {
        c := b;
    }
    if(c != b) {
        a := c*100;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c*100;
    } else {
        c := c*4;
    }
    if(c != b) {
        a := c*100;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*100;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*100;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*100;
    } else {
        a := c*4;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 99;
    if(b != a) {
        b := 3;
    } else {
        b := 9;
    }
    if(c <= b) {
        c := c*60;
    } else {
        c := c*5;
    }
    if(c >= b) {
        b := c*30;
    } else {
        b := c*2;
    }
    if(c <= b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c > b) {
        b := c*10;
    } else {
        c := c*4;
    }
    if(c < b) {
        a := c*10;
    } else {
        c := a*b;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*4;
    }
    if(c != b) {
        b := 4-a;
    } else {
        b := 4-b;
    }
	   if(b != c) {
        a := b-a;
    } else {
        c := a+b;
    }
    if(c == b) {
        a := c*a;
    } else {
        a := c*4;
    }
    if(c == b) {
        a := c*10;
    } else {
        c := c-4;
    }
    if(c == b) {
        b := c+10;
    } else {
        c := c*4;
    }
    if(c == b) {
        c := c-1030;
    } else {
        b := c*b;
    }
    if(c == b && a == b) {
        c := c*140;
    } else {
        c := c*45;
    }
    if(c != b) {
        b := c*10;
    } else {
        b := c*40;
    }
    a := 9;
}