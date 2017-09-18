import Data.Char
-- Binary Transmission
-- Encode
-- Convert String to array of bits

-- Convert Integer to bits
type Bit = Int
int2Bits :: Int -> [Bit]
int2Bits 1 = [1]
int2Bits 0 = [0]
int2Bits x = (x `mod` 2) : int2Bits (x `div` 2)

noo :: [Bit] -> Int
noo xs = length (filter (==1) xs)

parity :: [Bit] -> [Bit]
parity xs = if (odd (noo xs)) then xs++[1] else xs++[0]

-- Ensure that each byte array has 8 bits
make8 :: [Bit] -> [Bit]
make8 xs = take 8 (xs ++ repeat 0)

encode :: String -> [Bit]
encode  = concat . map (parity. make8 . int2Bits . ord)

-- Channel
channel :: [Bit] -> [Bit]
channel = id

-- Decode
-- Chop bits in pieces of 8
chop9 :: [Bit] -> [[Bit]]
chop9 [] = []
chop9 bits = take 9 bits : chop9 (drop 9 bits)

-- For each convert 8 to Int
bin2Int :: [Bit] -> Int
bin2Int xs = foldr (\x y -> x + 2 * y) (0) xs

checkParity :: [Bit] -> [Bit]
checkParity xs = if (parity (take 8 xs) == xs) then (take 8 xs) else error ("Invalid Bit")

decode :: [Bit] -> String
decode =  map (chr . bin2Int. checkParity) . chop9 -- map(chr.bin2Int) is one function and chop8 is second function.

-- Transmit
transmit :: String -> String
transmit = decode . channel . encode