import React, {useRef, useState} from "react";
import {Button} from "@material-ui/core";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Slide from "@material-ui/core/Slide";
import TextField from "@material-ui/core/TextField";

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

function ProjectDialogCreate({open, onClose}) {

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const onCommit = () => {
    onClose({
      name: title,
      description
    });
  }

  const onCancel = () => {
    onClose();
  }

  return (
    <Dialog
      fullWidth={true}
      maxWidth={"lg"}
      open={open}
      TransitionComponent={Transition}
      keepMounted
      onClose={onClose}
      aria-labelledby="alert-dialog-slide-title"
      aria-describedby="alert-dialog-slide-description">
      <DialogTitle id="alert-dialog-slide-title">{"Add my project"}</DialogTitle>
      <DialogContent>
        <DialogContent id="alert-dialog-slide-description">
          <div>
            <TextField
              id="name-field"
              label="Name"
              style={{ margin: 8 }}
              placeholder="Title"
              fullWidth
              margin="normal"
              InputLabelProps={{
                shrink: true,
              }}
              value={title}
              onChange={e => setTitle(e.target.value)}
              variant="outlined" />
          </div>
          <div>
            <TextField
              id="description-field"
              label="Text"
              style={{ margin: 8 }}
              placeholder="Description"
              fullWidth
              margin="normal"
              InputLabelProps={{
                shrink: true,
              }}
              value={description}
              onChange={e => setDescription(e.target.value)}
              variant="outlined" />
          </div>
        </DialogContent>
      </DialogContent>
      <DialogActions>
        <Button onClick={onCommit} color="primary">Ok</Button>
        <Button onClick={onCancel} color="primary">Cancel</Button>
      </DialogActions>
    </Dialog>
  )
}

export default ProjectDialogCreate