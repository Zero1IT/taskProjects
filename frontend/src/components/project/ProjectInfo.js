import React, {useContext, useEffect, useState} from "react";
import {Container, Header} from 'semantic-ui-react'
import {AuthenticateContext} from "../../App";

function ProjectInfo({projectId}) {
  const [project, setProject] = useState({});
  const {api} = useContext(AuthenticateContext);

  const loadProject = async () => {
    const res = await api.get("/projects/view/" + projectId);
    if (res.status === 200) {
      setProject(res.data);
    }
  };

  useEffect(() => {
    loadProject();
  }, []);

  return (
    <div>
      <Container text style={{ marginTop: '7em' }}>
        <Header as='h1'>{project.name}</Header>
        <p>{`This project created ${new Date(project.startDate).toLocaleDateString()}`}</p>
        <p>
          {project.description}
        </p>
      </Container>
    </div>
  )
}

export {
  ProjectInfo
}