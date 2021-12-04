/// <reference types="react" />
import { RevealProps } from "../Reveal";
declare type HingeProps = Omit<RevealProps, "keyframes" | "css">;
declare const Hinge: React.FC<HingeProps>;
export default Hinge;
