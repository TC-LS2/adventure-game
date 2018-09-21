import React from "react"
import { css } from "emotion"

const mapClassName = css`
  background: var(--main-color);
  color: var(--bold-color);
  font-size: 0.8rem;
`

const Map = ({ map, style }) => (
  <pre className={mapClassName} style={style}>
    {map}
  </pre>
)

export default Map
