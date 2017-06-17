var clock : int;

var hour : int;
var min : int;

var alarmSignal : int;
var alarmSet : int;
var alarmHour : int;
var alarmMin : int;
var alarmLength : int;

var hourAfterAlarm : int;
var minAfterAlarm : int;

procedure initClock(initHour : int, initMin : int) returns()
requires clock != 1;
requires initHour >= 0 && initHour < 24;
requires initMin >= 0 && initMin < 60;
ensures hour == initHour && min == initMin && clock == 1;
ensures hour < 24 && hour >=0 && min >= 0 && min < 60;
{
	hour := initHour;
	min := initMin;
	clock := 0;
}

procedure setTime(timeHour : int, timeMin : int) returns()
requires clock == 1;
requires timeHour >= 0 && timeHour < 24;
requires timeMin >= 0 && timeMin < 60;
ensures hour == timeHour && min == timeMin;
ensures hour < 24 && hour >=0 && min >= 0 && min < 60;
{
	hour := timeHour;
	min := timeMin;
}

procedure setAlarm(alarmAlarmHour : int, alarmAlarmMin : int, alarmAlarmLength : int) returns()
requires clock == 1;
requires alarmAlarmLength > 0 && alarmAlarmLength <= 10;
requires alarmAlarmHour >= 0 && alarmAlarmHour < 24;
requires alarmAlarmMin >= 0 && alarmAlarmMin < 60;
ensures alarmHour == alarmAlarmHour && alarmMin == alarmAlarmMin && alarmLength == alarmAlarmLength && alarmSet == 1;
ensures alarmHour < 24 && alarmHour >=0 && alarmMin >= 0 && alarmMin < 60;
{
  hour := alarmAlarmHour;
	min := alarmAlarmMin;
	alarmLength := alarmAlarmLength;
	alarmSet := 1;
}

procedure runClock() returns()
requires clock == 1 && alarmSet == 1;
requires hour >= 0 && hour < 24;
requires min >= 0 && min < 60;
ensures hour == alarmHour && min == alarmMin;
ensures hour < 24 && hour >=0 && min >= 0 && min < 60;
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
requires hour >= 0 && hour < 24;
requires min >= 0 && min < 60;
ensures (old(min + alarmLength) < 60) ==> min == old(min + alarmLength);
ensures (old(min + alarmLength) > 60 && old(hour) < 24) ==> hour == old(hour) + 1 && min == old(min + alarmLength) - 60;
ensures (old(min + alarmLength) > 60 && old(hour) == 24) ==> hour == 0 && min == old(min + alarmLength) - 60;
ensures hour < 24 && hour >=0 && min >= 0 && min < 60;
ensures alarmSignal == 0;
{
	alarmSignal := 1;
	if (min + alarmLength > 60 && hour < 24){
		hourAfterAlarm := hour+1;
		minAfterAlarm := min+alarmLength-60;
	}
	if (min + alarmLength > 60 && hour == 24){
		hourAfterAlarm := 0;
		minAfterAlarm := min+alarmLength-60;
	}
	if (min + alarmLength < 60){
		hourAfterAlarm := hour;
		minAfterAlarm := min+alarmLength;
	}
	while (!(hour == hourAfterAlarm && min == minAfterAlarm)) invariant (min >= 0) && (min < 60) && (hour >= 0) && (hour < 24);{
		if(min >= 60){
			min := 0;
			hour := hour+1;
		} else{
			min := min + 1;
		}
		if(hour > 24){
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
}