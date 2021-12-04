/// <reference types="react" />
import { RevealProps } from "../Reveal";
declare type JackInTheBoxProps = Omit<RevealProps, "keyframes" | "css">;
declare const JackInTheBox: React.FC<JackInTheBoxProps>;
export default JackInTheBox;
