import axios from "axios";
import { Button } from "@material-ui/core";
import { useState } from "react";
import { useHistory } from "react-router";
import "./Question.css";
import ErrorMessage from "../ErrorMessage/ErrorMessage";
import {decode} from 'html-entities';

const Question = ({
  currQues,
  setCurrQues,
  questions,
  options,
  setScore,
  score,
  setQuestions,

}) => {
  const [selected, setSelected] = useState();
  const [error, setError] = useState(false);
  const [correct, setCorrect] = useState();

  const history = useHistory();
  
  const handleSelect = (i) => {      

    if (selected === i && selected === correct) return "right";
    else if (selected === i && selected !== correct) return "wrong";
    else if (i === correct) return "right";  
    
  };


  const handleCheck = (i) => {

    setSelected(i);

 
    axios.post(`http://localhost:8080/api/checkanswer/${currQues}`,
                 {
                   "userAnswer": i                                      
                } 
              ).then(res=>      
                               {                             
                                setCorrect(res.data[1]);
                                if (res.data[0]) setScore(score + 1);                                
                                setError(false);}
                    )
  };

  const handleNext = () => {
    if (currQues > 3 && selected) {
      history.push("/result");
    } else if (selected) {
      setCurrQues(currQues + 1);
      setSelected();
    } else setError("Please select an option first");
  };

  const handleQuit = () => {
    setCurrQues(0);
    setQuestions();
  };

  return (
    <div className="question">
      <h1>Question {currQues + 1} :</h1>

      <div className="singleQuestion">
        <h2>{decode(questions[currQues].question)}</h2>
        <div className="options">
          {error && <ErrorMessage>{error}</ErrorMessage>}
          {options &&
            options.map((i) => (
              <button
                className={`singleOption  ${selected && handleSelect(i)}`}
                key={i}
                onClick={() => handleCheck(i)}
                disabled={selected}
              >
                {i}
              </button>
            ))}
        </div>
        <div className="controls">
          <Button
            variant="contained"
            color="secondary"
            size="large"
            style={{ width: 185 }}
            href="/"
            onClick={() => handleQuit()}
          >
            Quit
          </Button>
          <Button
            variant="contained"
            color="primary"
            size="large"
            style={{ width: 185 }}
            onClick={handleNext}
          >
            {currQues > 5 ? "Submit" : "Next Question"}
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Question;
