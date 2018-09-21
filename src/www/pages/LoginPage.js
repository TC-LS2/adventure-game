import React from "react"

import StateLoginForm from "../ducks/login/components/StateLoginForm"
import BasicLayout from "../layouts/BasicLayout"

const Login = () => (
  <BasicLayout breadCrumb="> Builder">
    <StateLoginForm />
  </BasicLayout>
)

export default Login
