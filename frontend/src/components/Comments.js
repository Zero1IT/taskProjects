import { Button, Comment, Form, Header } from 'semantic-ui-react'
import React, {useContext, useEffect, useState} from "react";
import {AuthenticateContext} from "../App";

function Comments({projectId}) {

  const [comms, setComms] = useState([]);
  const {api} = useContext(AuthenticateContext);
  const [commentText, setCommentText] = useState("");

  const loadComments = async () => {
    const res = await api.get("/comments/" + projectId);
    if (res.status === 200) {
      setComms(res.data);
    }
  }

  const addComment = async () => {
    const res = await api.post("/comments/"+projectId, {
      text: commentText
    });
    if (res.status === 200) {
      setComms([...comms, res.data]);
    }
  }

  useEffect(() => {
    loadComments();
  }, []);

  return (
    <Comment.Group minimal threaded>
      <Header as='h3' dividing>
        Comments
      </Header>
      {
        comms.map(elem => (
          <Comment>
            <Comment.Content>
              <Comment.Author as='a'>{elem.user.nickname}</Comment.Author>
              <Comment.Metadata>
                <div>{new Date(elem.date).toLocaleDateString()}</div>
              </Comment.Metadata>
              <Comment.Text>{elem.text}</Comment.Text>
            </Comment.Content>
          </Comment>
        ))
      }
      <Form reply>
        <Form.TextArea value={commentText} onChange={e => setCommentText(e.target.value)} />
        <Button onClick={addComment}  content='Add comment' labelPosition='left' icon='edit' primary />
      </Form>
    </Comment.Group>
  )
}

export {
  Comments
}