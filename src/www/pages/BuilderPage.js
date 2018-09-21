import React from "react"

import StateWorldEditor from "../ducks/world-builder/components/StateWorldEditor"
import BasicLayout from "../layouts/BasicLayout"

const BuilderPage = () => (
  <BasicLayout breadCrumb="> Builder">
    <StateWorldEditor />
  </BasicLayout>
)

export default BuilderPage
