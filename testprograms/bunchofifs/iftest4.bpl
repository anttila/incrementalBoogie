var a : int;
var b : int;
var c : int;

procedure iftest() returns () ensures a == 9; modifies a; modifies b; modifies c; {
    b := 9;
    a := 0;
    c := 8;
    if(b != c) {
        a := a;
    } else {
        c := a;
    }
    if(c < b) {
        a := b*100;
    } else {
        a := b*4;
    }
    if(c > b) {
        c := a*100;
    } else {
        c := b*4;
    }
    if(c == b) {
        a := c*100;
    } else {
        a := c*40;
    }
    if(c == b) {
        a := b*30;
    } else {
        c := c*40;
    }
    if(c == 1 && b == 2) {
        a := c*1000;
    } else {
        c := c-40;
    }
    if(c <= b) {
        a := c+100;
    } else {
        a := c*4;
    }
    a := 99;
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
        if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
        if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
       if(b == c) {
        a := b;
    } else {
        c := b;
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
    if(b == a) {
        a := 3;
    } else {
        a := 9;
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
    if(c == b) {
        a := 4;
    } else {
        a := 4;
    }
}