import React, {useContext, useEffect, useState} from "react";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import {List} from 'semantic-ui-react'
import {AuthenticateContext} from "../../App";

function AddUserPanel({projectId}) {

  const [nickname, setNickname] = useState("");
  const [progress, setProgress] = useState(false);
  const [users, setUsers] = useState([]);
  const {api} = useContext(AuthenticateContext);

  const loadUsers = async () => {
    const res = await api.get("/projects/users/" + projectId);
    if (res.status === 200) {
      setUsers(res.data);
    }
  };

  useEffect(() => {
    loadUsers();
  }, [])

  const handleButtonClick = async () => {
    setProgress(true);
    const res = await api.post(`/projects/user?userNickname=${nickname}&project=${projectId}`);
    if (res.status === 200 && res.data) {
      setUsers(prev => [...prev, res.data]);
    }
    setProgress(false);
  }

  return (
    <div>
      <List>
        {
          users.map(user => (
            <List.Item>
              <List.Content>
                <List.Header as='a'>{user.nickname}</List.Header>
                <List.Description>
                  {user.activeAccount ? 'Omg, he still active' : 'Oh, no! He is no active'}
                </List.Description>
              </List.Content>
            </List.Item>
          ))
        }
      </List>
      <div>
        <TextField
          id="nickname-text"
          label="Nickname"
          helperText=""
          variant="outlined"
          value={nickname}
          onChange={e => setNickname(e.target.value)}/>
        <Button
          variant="contained"
          color="primary"
          disabled={progress}
          onClick={handleButtonClick}>
          Accept
        </Button>
      </div>
    </div>
  )
}

export {
  AddUserPanel
}