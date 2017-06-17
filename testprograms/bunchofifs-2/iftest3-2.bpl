var a : int;
var b : int;
var c : int;

procedure iftest() returns () ensures a == 9; modifies a; modifies b; modifies c; {
    b := 9;
    a := 0;
    c := 8;
    if(b != c) {
        b := c;
    } else {
        b := a;
    }
    if(b <= c) {
        b := a*1;
    } else {
        b := a*5;
    }
    if(c < a) {
        c := b-100;
    } else {
        c := b+4;
    }
    if(a > b) {
        c := c-50;
    } else {
        c := c+3;
    }
    if(c <= b) {
        a := c*100;
    } else {
        a := c*4;
    }
    if(c >= b) {
        a := c*100;
    } else {
        a := c*4;
    }
    if(a != b) {
        a := b*2;
    } else {
        b := b*3;
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
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
    }
    a := 99;
    if(b != a) {
        a := 5;
    } else {
        a := 2;
    }
    if(a >= b) {
        a := a*20;
    } else {
        a := b*3;
    }
    if(a <= b) {
        b := a*2;
    } else {
        b := b*4;
    }
    if(c == a) {
        c := a*200;
    } else {
        c := a*7;
    }
    if(c >= c) {
        a := b+10;
    } else {
        a := c-1;
    }
    if(b <= c) {
        b := b-a;
    } else {
        a := a*b;
    }
    if(c+5 == b) {
        c := b-5*a;
    } else {
        b := c*3+2*a;
    }
    if(c >= b-a) {
        b := 5;
    } else {
        c := 8;
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
    a := 9;
}