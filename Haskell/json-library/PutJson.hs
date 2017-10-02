module PutJson where

import Data.List (intercalate)
import SimpleJson

renderJValue :: JValue -> String

renderJValue (JString a) = show a
renderJValue (JNumber n) = show n
renderJValue (JBool b) = show b
renderJValue JNull = "null"

renderJValue (JObject x) = "{" ++ renderPairs x ++ "}"
        where
          renderPairs [] = ""
          renderPairs ps = intercalate "," (map renderPair ps)
          renderPair (k,v) = show k ++ " : " ++ renderJValue v

renderJValue (JArray []) = ""
renderJValue (JArray a) = "[" ++ values a ++ "]"
        where
          values x = intercalate "," (map renderJValue x)

putJValue :: JValue -> IO ()
putJValue jv = putStrLn (renderJValue jv)