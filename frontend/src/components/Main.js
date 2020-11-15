import React from "react";
import {Container} from "@material-ui/core";
import {ProjectsListViewer} from "./project/ProjectsListViewer";

function Main() {
  return (
    <Container>
      <ProjectsListViewer />
    </Container>
  )
}

export {
  Main
}