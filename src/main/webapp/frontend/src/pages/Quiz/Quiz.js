import { CircularProgress } from "@material-ui/core";
import { useEffect, useState } from "react";
import Question from "../../components/Question/Question";
import Timer from "../../components/Timer/Timer";

import "./Quiz.css";

const Quiz = ({ name, questions, score, setScore, setQuestions}) => {
  const [options, setOptions] = useState();
  const [currQues, setCurrQues] = useState(0);
  const [loading, setLoading] = useState(false);
      
  useEffect(() => {
    
   setOptions(
      questions &&
        handleShuffle([
          questions[currQues]?.correct_answer,
          ...questions[currQues]?.incorrect_answers,
        ])
    );

    setLoading(questions && true)
    
  
  }, [currQues, questions]);


  const handleShuffle = (options) => {
    return options.sort(() => Math.random() - 0.5);
  };

  return (
    <div className="quiz">
      <span className="subtitle">Welcome, {name}</span>
    {loading? (<span className="timer"> <Timer/> </span>): (<p className="loading"> LOADING... </p>)} 
     {questions ? (
        <>
          <div className="quizInfo">
            <span> {questions[currQues].category}</span>
             
             <span>
        
                 Score : {score}
            </span>
            
          </div>
          <Question
            currQues={currQues}
            setCurrQues={setCurrQues}
            questions={questions}
            options={options}                 
            score={score}
            setScore={setScore}
            setQuestions={setQuestions}   
     
                 
          />
          
        </>
      ) : (
        <CircularProgress
          style={{ margin: 100 }}
          color="inherit"
          size={150}
          thickness={1}
        />
      )}
    </div>
  );
};

export default Quiz;


