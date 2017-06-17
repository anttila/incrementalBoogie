var slowIterator : int;
var a : int;
var b : int;
var c : int;
var d : int;
var e : int;
var f : int;
var g : int;
var h : int;
procedure add(x : int, y : int) returns (z : int)
ensures z == x + y;
{
	z := x + y;
}

procedure sub(x : int, y : int) returns (z : int)
ensures z == x - y;
{
	z := x + y;
}

procedure mul(x : int, y : int) returns (z : int)
ensures z == x * y;
{
	z := x * x;
}

procedure slowMul(slowMulx : int, slowMuly : int) returns (slowMulz : int)
ensures slowMulz == slowMulx * slowMuly;
{
	slowIterator := 0;
	slowMulz:= 0;
	while (slowIterator <= slowMuly) invariant slowMulz == slowMulx * slowIterator; {
		slowMulz := slowMulz + slowMulx;
    slowIterator := slowIterator + 1;
	}
}

procedure main () returns ()
ensures a == 1;
{
  havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);
  
  havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);
    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

    havoc a, b;
  
  call c := slowMul(a, b);
  call d := mul(a, b);
  assert c == d;
  assert c == a * b;
  
  call c := mul(a, b);
  call d := mul(b, a);
  assert c == d;
  
  havoc c;
  call d := mul(a, b);
  call e := mul(d, c);
  call f := mul(b, c);
  call g := mul(c, f);
  assert (g == e);
  
  call c := add(a, b);
  call d := add(b, a);
  assert (c == d);
  assert (c == a + b);
  
  havoc c;
  call d := add(a, b);
  call e := add(d, c);
  call f := add(b, c);
  call g := add(a, f);
  assert (g == f);
  
  assume a != b;
  call c := sub(a,b);
  call d := sub(b,a);
  assert (c != d);
  
  havoc a,b;
  assume a!=b && b!=c && a!=c;
  call d := sub(a, b);
  call e := sub(d, c);
  call f := sub(b, c);
  call g := sub(a, f);
  assert (g != e);

  a:=1;
}