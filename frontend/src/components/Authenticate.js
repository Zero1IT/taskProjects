import React, {useContext, useState} from "react";
import {Container} from "@material-ui/core";
import Paper from '@material-ui/core/Paper';
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import {authenticate} from "../api/authentication";
import {AuthenticateContext} from "../App";
import {useHistory} from "react-router-dom";


function Authenticate() {

  const [nickname, setNickname] = useState("");
  const [password, setPassword] = useState("");
  const [progress, setProgress] = useState(false);
  const {update} = useContext(AuthenticateContext);
  const history = useHistory();

  const handleButtonClick = () => {
    setProgress(true);
    authenticate({
      nickname, password
    }).then(obj => {
      if (obj) {
        history.replace("/");
        update(obj);
      } else {
        setProgress(false);
      }
    }).catch(() => {
      setProgress(false);
    });
  }

  return (
    <Container>
      <Paper elevation={3} style={{marginTop: "20px"}}>
        <div>
          <TextField
            id="nickname-text"
            label="Nickname"
            helperText=""
            variant="outlined"
            value={nickname}
            onChange={e => setNickname(e.target.value)}/>
          <TextField
            id="password-text"
            label="Password"
            helperText=""
            type="password"
            variant="outlined"
            value={password}
            onChange={e => setPassword(e.target.value)}/>
          <Button
            variant="contained"
            color="primary"
            disabled={progress}
            onClick={handleButtonClick}>
            Accept
          </Button>
        </div>
      </Paper>
    </Container>
  )
}

export {
  Authenticate
}