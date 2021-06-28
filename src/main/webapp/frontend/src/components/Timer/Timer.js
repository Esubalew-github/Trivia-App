import React from 'react';
import { useTimer } from 'react-timer-hook';
import { useHistory } from "react-router";

function Timer({ expiryTimestamp }) {
  const history = useHistory();
  const {
    seconds,
    minutes,
    hours,
    //days,
    
  } = useTimer({ expiryTimestamp, onExpire: () => history.push("/result")});

  return (
    <div style={{textAlign: 'center'}}> 
     <p>You Have 1 Minute To Finish This Quiz</p>
      <div style={{fontSize: '20px'}}>
        <span>{hours}</span>:<span>{minutes}</span>:<span>{seconds}</span>
        </div>
      
    </div>
  );
}

export default function App() {
  const time = new Date();
  time.setSeconds(time.getSeconds() + 60); // 60 seconds timer
  return (
    <div>
      <Timer expiryTimestamp={time} />
    </div>
  );
}
