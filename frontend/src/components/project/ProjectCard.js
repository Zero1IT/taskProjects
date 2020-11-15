import CardHeader from "@material-ui/core/CardHeader";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import {Link} from "react-router-dom";
import Button from "@material-ui/core/Button";
import Card from "@material-ui/core/Card";
import React, {useContext, useState} from "react";
import {AuthenticateContext} from "../../App";

function download(filename, text) {
  const element = document.createElement('a');
  element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
  element.setAttribute('download', filename);
  element.style.display = 'none';
  document.body.appendChild(element);
  element.click();
  document.body.removeChild(element);
}

function writeUserAsCsv(user) {
  return `${user.id};${user.nickname};${user.activeAccount}`;
}

function writeUsersArrayAsCsv(array) {
  if (array) {
    const csvs = array.map(u => writeUserAsCsv(u));
    return csvs.join(";");
  }
}

function writeCommentsAsCsv(comments) {
  if (comments) {
    const csvs = comments.map(c => `${c.id};${c.date};${c.text};${writeUserAsCsv(c.user)}`);
    return csvs.join(";");
  }
}

function ProjectCard({elem}) {

  const [finished, setFinished] = useState(!!elem.finishDate);
  const {api} = useContext(AuthenticateContext);
  const [progress, setProgress] = useState(false);

  const exportFile = async () => {
    setProgress(true);
    const res = await api.get("/projects/load/"+elem.id);
    if (res.status === 200) {
      const start = `${elem.id};${elem.name};${elem.description};${elem.startDate};${elem.finishDate}`;
      download(elem.name+elem.id+".csv",
        `${start};${writeUserAsCsv(elem.creator)};${writeUsersArrayAsCsv(elem.users)};${writeCommentsAsCsv(elem.comments)}`)
    }
    setProgress(false);
  };

  const finishThis = async () => {
    setProgress(true);
    const res = await api.delete("/projects/"+elem.id);
    if (res.data) {
      setFinished(!!res.data.finishDate);
    }
    setProgress(false);
  };

  return (
    <Card>
      <CardHeader
        title={`Name : ${elem.name}`}
        subheader={`Created at : ${new Date(elem.startDate).toLocaleString()}`}
      />
      <CardContent>
        <Typography variant="h5" gutterBottom>
          Creator: {elem.creator.nickname}
        </Typography>
        <Link to={`/project/${elem.id}`}>
          <Button
            disabled={progress}
            variant="contained"
            color="primary">
            Open
          </Button>
        </Link>
        {
          !finished &&
          <Button
            disabled={progress}
            onClick={finishThis}
            color="secondary"
            variant="contained">
            Finish
          </Button>
        }
        <Button
          disabled={progress}
          color="default"
          onClick={exportFile}
          variant="contained">
          Export CSV
        </Button>
      </CardContent>
    </Card>
  )
}

export {
  ProjectCard
}