import React, {useEffect, useState} from "react";
import {SignIn} from "./components/SignIn";
import {updateApiTokens} from "./api/authentication";
import {
  BrowserRouter as Router, Route, Switch
} from "react-router-dom";
import {Authenticate} from "./components/Authenticate";
import {Registration} from "./components/Registration";
import {Main} from "./components/Main";
import {ProjectView} from "./components/project/ProjectView";
import 'semantic-ui-css/semantic.min.css';
import {Charts} from "./components/Charts";

const AuthenticateContext = React.createContext({api: null, update: null})

function App() {

  const updateContext = (newApi) => {
    if (newApi && newApi.get && newApi.post) {
      setContext({api: newApi, update: updateContext});
    }
  };

  const [context, setContext] = useState({api: null, update: updateContext});

  const loadApi = async () => {
    const nApi = await updateApiTokens();
    updateContext(nApi);
  };

  useEffect(() => {
    loadApi().catch(e => console.log(`load api: ${e}`));
  }, []);
  return (
    <AuthenticateContext.Provider value={context}>
      <Router>
        <div>
          <Switch>
            <Route path="/authenticate">
              <Authenticate />
            </Route>
            <Route path="/registration">
              <Registration />
            </Route>
            <Route path="/project/:id">
              <ProjectView />
            </Route>
            <Route path="/statistic">
              <Charts />
            </Route>
            <Route path="/">
              {context.api ? <Main /> : <SignIn />}
            </Route>
          </Switch>
        </div>
      </Router>
    </AuthenticateContext.Provider>
  );
}

export {
  App,
  AuthenticateContext
}
