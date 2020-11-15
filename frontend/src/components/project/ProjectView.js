import React from "react";
import {ProjectInfo} from "./ProjectInfo";
import {Comments} from "../Comments";
import {useParams} from "react-router-dom";
import {AddUserPanel} from "./AddUserPanel";

function ProjectView() {

  const {id} = useParams();

  return (
    <div>
      <AddUserPanel projectId={id} />
      <ProjectInfo projectId={id}/>
      <Comments projectId={id} />
    </div>
  )
}

export {
  ProjectView
}