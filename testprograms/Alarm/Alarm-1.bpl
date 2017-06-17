var clock : int;

var hour : int;
var min : int;

var alarmSignal : int;
var alarmSet : int;
var alarmHour : int;
var alarmMin : int;
var alarmLength : int;

var mainHour : int;
var mainMin : int;
var mainAlarmHour : int;
var mainAlarmMin : int;
var mainAlarmLength : int;

var currentAlarmTime : int;

procedure initClock(initHour : int, initMin : int) returns()
requires clock != 1;
ensures hour == initHour && min == initMin && clock == 1;
{
	hour := initHour;
	min := initMin;
	clock := 0;
}

procedure setTime(timeHour : int, timeMin : int) returns()
requires clock == 1;
ensures hour == timeHour && min == timeMin;
{
	hour := timeHour;
	min := timeMin;
}

procedure setAlarm(alarmAlarmHour : int, alarmAlarmMin : int, alarmAlarmLength : int) returns()
requires clock == 1;
requires alarmAlarmLength > 0 && alarmAlarmLength <= 10;
ensures alarmHour == alarmAlarmHour && alarmMin == alarmAlarmMin && alarmLength == alarmAlarmLength && alarmSet == 1;
{
  hour := alarmAlarmHour;
	min := alarmAlarmMin;
	alarmLength := alarmAlarmLength;
	alarmSet := 1;
}

procedure runClock() returns()
requires clock == 1 && alarmSet == 1;
ensures hour == alarmHour && min == alarmMin;
{
	while (!(hour == alarmHour && min == alarmMin)) invariant (min >= 0) && (min < 60) && (hour >= 0) && (hour < 24); {
		if(min >= 60){
			min := 0;
			hour := hour+1;
		} else{
			min := min + 1;
			hour := hour;
		}
		if(hour > 24){
			hour := 0;
		}
	}
}

procedure executeAlarm() returns()
requires clock == 1 && alarmSet == 1;
ensures (min < 60) ==> min == min + alarmLength;
ensures (min > 60 && hour < 24) ==> hour == hour + 1 && min == min + alarmLength - 60;
ensures (min > 60 && hour == 24) ==> hour == 0 && min == min + alarmLength - 60;
ensures alarmSignal == 0;
{
	alarmSignal := 1;
  	currentAlarmTime := alarmLength;
	while (currentAlarmTime > 0) invariant (min >= 0) && (min < 60) && (hour >= 0) && (hour < 24);{
		if(min == 59){
			min := 0;
			hour := hour+1;
		} else{
			min := min + 1;
		}
		if(hour == 24){
			hour := 0;
			hour := hour;
		}	
	}
}


procedure resetClock() returns()
ensures alarmSet == 0 && clock == 0;
{
	alarmSet := 0;
	clock := 0;
}

procedure main() returns ()
ensures hour < 24 && hour >=0 && min >= 0 && min < 60;
{

		clock := 1-2;

	mainHour := 23;
	mainMin := 59;
	mainAlarmHour := 1;
	mainAlarmMin := 34;
	mainAlarmLength := 10;

	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	mainHour := 15;
	mainMin := 43;
	mainAlarmHour := 23;
	mainAlarmMin := 50;
	mainAlarmLength := 10;

	call setTime(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 18;
	mainMin := 9;
	mainAlarmHour := 5;
	mainAlarmMin := 14;
	mainAlarmLength := 6;
	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);

	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 0;


	clock := 1-2;

	mainHour := 23;
	mainMin := 59;
	mainAlarmHour := 1;
	mainAlarmMin := 34;
	mainAlarmLength := 10;

	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	mainHour := 15;
	mainMin := 43;
	mainAlarmHour := 23;
	mainAlarmMin := 50;
	mainAlarmLength := 10;

	call setTime(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 18;
	mainMin := 9;
	mainAlarmHour := 5;
	mainAlarmMin := 14;
	mainAlarmLength := 6;
	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);

	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 0;

		clock := 1-2;

	mainHour := 23;
	mainMin := 59;
	mainAlarmHour := 1;
	mainAlarmMin := 34;
	mainAlarmLength := 10;

	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	mainHour := 15;
	mainMin := 43;
	mainAlarmHour := 23;
	mainAlarmMin := 50;
	mainAlarmLength := 10;

	call setTime(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 18;
	mainMin := 9;
	mainAlarmHour := 5;
	mainAlarmMin := 14;
	mainAlarmLength := 6;
	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);

	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 0;

		clock := 1-2;

	mainHour := 23;
	mainMin := 59;
	mainAlarmHour := 1;
	mainAlarmMin := 34;
	mainAlarmLength := 10;

	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	mainHour := 15;
	mainMin := 43;
	mainAlarmHour := 23;
	mainAlarmMin := 50;
	mainAlarmLength := 10;

	call setTime(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 18;
	mainMin := 9;
	mainAlarmHour := 5;
	mainAlarmMin := 14;
	mainAlarmLength := 6;
	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);

	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 0;

		clock := 1-2;

	mainHour := 23;
	mainMin := 59;
	mainAlarmHour := 1;
	mainAlarmMin := 34;
	mainAlarmLength := 10;

	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	mainHour := 15;
	mainMin := 43;
	mainAlarmHour := 23;
	mainAlarmMin := 50;
	mainAlarmLength := 10;

	call setTime(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 18;
	mainMin := 9;
	mainAlarmHour := 5;
	mainAlarmMin := 14;
	mainAlarmLength := 6;
	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);

	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 0;
	clock := 1-2;

	mainHour := 23;
	mainMin := 59;
	mainAlarmHour := 1;
	mainAlarmMin := 34;
	mainAlarmLength := 10;

	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	mainHour := 15;
	mainMin := 43;
	mainAlarmHour := 23;
	mainAlarmMin := 50;
	mainAlarmLength := 10;

	call setTime(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 18;
	mainMin := 9;
	mainAlarmHour := 5;
	mainAlarmMin := 14;
	mainAlarmLength := 6;
	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);

	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 0;


	clock := 1-2;

	mainHour := 23;
	mainMin := 59;
	mainAlarmHour := 1;
	mainAlarmMin := 34;
	mainAlarmLength := 10;

	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	mainHour := 15;
	mainMin := 43;
	mainAlarmHour := 23;
	mainAlarmMin := 50;
	mainAlarmLength := 10;

	call setTime(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 18;
	mainMin := 9;
	mainAlarmHour := 5;
	mainAlarmMin := 14;
	mainAlarmLength := 6;
	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);

	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 0;

		clock := 1-2;

	mainHour := 23;
	mainMin := 59;
	mainAlarmHour := 1;
	mainAlarmMin := 34;
	mainAlarmLength := 10;

	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	mainHour := 15;
	mainMin := 43;
	mainAlarmHour := 23;
	mainAlarmMin := 50;
	mainAlarmLength := 10;

	call setTime(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 18;
	mainMin := 9;
	mainAlarmHour := 5;
	mainAlarmMin := 14;
	mainAlarmLength := 6;
	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);

	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 0;

		clock := 1-2;

	mainHour := 23;
	mainMin := 59;
	mainAlarmHour := 1;
	mainAlarmMin := 34;
	mainAlarmLength := 10;

	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	mainHour := 15;
	mainMin := 43;
	mainAlarmHour := 23;
	mainAlarmMin := 50;
	mainAlarmLength := 10;

	call setTime(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 18;
	mainMin := 9;
	mainAlarmHour := 5;
	mainAlarmMin := 14;
	mainAlarmLength := 6;
	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);

	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 0;

		clock := 1-2;

	mainHour := 23;
	mainMin := 59;
	mainAlarmHour := 1;
	mainAlarmMin := 34;
	mainAlarmLength := 10;

	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	mainHour := 15;
	mainMin := 43;
	mainAlarmHour := 23;
	mainAlarmMin := 50;
	mainAlarmLength := 10;

	call setTime(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);
	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 18;
	mainMin := 9;
	mainAlarmHour := 5;
	mainAlarmMin := 14;
	mainAlarmLength := 6;
	call initClock(mainHour, mainMin);
	call setAlarm(mainAlarmHour, mainAlarmMin, mainAlarmLength);

	call runClock();
	call executeAlarm();

	call resetClock();

	mainHour := 0;
}