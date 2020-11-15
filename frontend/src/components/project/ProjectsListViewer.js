import React, {useContext, useEffect, useState} from "react";
import {AuthenticateContext} from "../../App";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import {Container, Dropdown, Menu,} from 'semantic-ui-react'
import {getRole} from "../../api/authentication";
import ProjectDialogCreate from "./ProjectDialogCreate";
import {ProjectCard} from "./ProjectCard";

function ProjectsListViewer({all}) {

  const [projects, setProjects] = useState([]);
  const {api} = useContext(AuthenticateContext);
  const [showAll, setShowAll] = useState(false)
  const [openDialog, setOpenDialog] = useState(false);
  const [lastPage, setLastPage] = useState(true)
  const [page, setPage] = useState(1);
  const [loadAll, setLoadAll] = useState(false);

  const loadProjects = async () => {
    console.log((loadAll ? "/projects/all/" : "/projects/") + page);
    const res = await api.get((loadAll ? "/projects/all/" : "/projects/") + page);
      if (res.status === 200) {
        if (page === 1) {
          setProjects(res.data.content);
        } else {
          setProjects((prev) => [...prev, ...res.data.content]);
        }
        setLastPage(res.data.last);
        if (!res.data.last) {
          setPage((prev) => prev + 1);
        }
    }
    const role = getRole();
    if (role) {
      setShowAll(role === "ADMIN");
    }
  };

  useEffect(() => {
    setPage(1);
    setLastPage(true);
    loadProjects().catch(console.log);
  }, [loadAll]);

  const addProjectClick = async () => {
    setOpenDialog(true);
  };

  const onCloseDialog = async project => {
    setOpenDialog(false);
    if (project) {
      const res = await api.post("/projects", project);
      if (res.status === 200 && lastPage) {
        setProjects([...projects, res.data]);
      }
    }
  };

  return (
    <div>
      <Menu fixed='top' inverted>
        <Container>
          <Menu.Item as='a' header>
            Control panel
          </Menu.Item>
          <Menu.Item as='a'>Home</Menu.Item>
          <Menu.Item as='a'>Statistics</Menu.Item>
          <Dropdown item simple text='Actions'>
            <Dropdown.Menu>
              <Dropdown.Item onClick={addProjectClick}>Add project</Dropdown.Item>
              <Dropdown.Item onClick={() => setLoadAll(false)}>Show only my</Dropdown.Item>
              {showAll && <Dropdown.Item onClick={() => setLoadAll(true)}>Show all projects</Dropdown.Item>}
            </Dropdown.Menu>
          </Dropdown>
        </Container>
      </Menu>
      <Grid
        style={{marginTop: "50px"}}
        container
        spacing={2}
        direction="row"
        justify="flex-start"
        alignItems="flex-start"
      >
        {projects.map(elem => (
          <Grid item xs={12} sm={6} md={3} key={elem.id}>
            <ProjectCard elem={elem} />
          </Grid>
        ))}
      </Grid>
      <Button
        variant="contained"
        color="primary"
        disabled={lastPage}
        onClick={loadProjects}>
        Load More
      </Button>
      <ProjectDialogCreate onClose={onCloseDialog} open={openDialog} />
    </div>
  )
}

export {
  ProjectsListViewer
}