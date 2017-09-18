import Data.Char
import Data.List

twice :: (a -> a) -> a -> a
twice f x = f (f x)

-- Define length in terms of foldr
lengthr :: [a] -> Int
lengthr = foldr (\_ n -> 1 + n) 0

-- Opposite of cons
snoc :: a -> [a] -> [a]
snoc x [] = [x]
snoc x xs = xs ++ [x]

-- Reverse using foldr
reverser :: [a] -> [a]
reverser = foldr snoc []

-- Using map function
testmap = map (*2) [1,2,3]
testfilter = filter (/=2) [1,2,3]

-- Binary Transmission
-- Encode
-- Convert String to array of bits

-- Convert Integer to bits
type Bit = Int
int2Bits :: Int -> [Bit]
int2Bits 1 = [1]
int2Bits 0 = [0]
int2Bits x = (x `mod` 2) : int2Bits (x `div` 2)

-- Ensure that each byte array has 8 bits
make8 :: [Bit] -> [Bit]
make8 xs = take 8 (xs ++ repeat 0)

encode :: String -> [Bit]
encode  = concat . map (make8 . int2Bits . ord)

-- Channel
channel :: [Bit] -> [Bit]
channel = id

-- Decode
-- Chop bits in pieces of 8
chop8 :: [Bit] -> [[Bit]]
chop8 [] = []
chop8 bits = take 8 bits : chop8 (drop 8 bits)

-- For each convert 8 to Int
bin2Int :: [Bit] -> Int
bin2Int xs = foldr (\x y -> x + 2 * y) (0) xs

decode :: [Bit] -> String
decode =  map (chr . bin2Int) . chop8 -- map(chr.bin2Int) is one function and chop8 is second function.

-- Transmit
transmit :: String -> String
transmit = decode . channel . encode

-- Count number of times a value occurs in a list
countIt :: Eq a => [a] -> a -> Int
countIt [] _ = 0
countIt xs x = sum [b | (a, b) <- zip xs (repeat 1), a == x]

countIt2 :: Eq a => a -> [a] -> Int
countIt2 x = length . filter (==x)

-- Remove duplicates from a list
rmdups :: Eq a => [a] -> [a]
rmdups [] = []
rmdups (x:xs) = x : filter(/=x) (rmdups xs)

-- Create result with increasing order of votes
voteResult :: Ord a => [a] -> [(Int, a)]
voteResult vs = sort [(countIt2 v vs, v) | v <- rmdups vs]

winner :: Ord a => [a] -> a
winner = snd . last . voteResult

-- Decide if all elements os a list satisfy a predicate
allx :: (a -> Bool) -> [a] -> Bool
allx f xs = length xs == length (filter f xs)

-- Decide if any elements os a list satisfy a predicate
anyx :: (a -> Bool) -> [a] -> Bool
anyx f xs = length (filter f xs) > 0

-- Select elements from a list that satisfy a predicate
selectx :: (a -> Bool) -> [a] -> [a]
selectx f xs = filter f xs

-- Remove elements from a list that satisfy a predicate
removex :: (a -> Bool) -> [a] -> [a]
removex f xs = filter (not . f) xs

-- Redefine functions map f and filter p using foldr
mapx :: (a -> b) -> [a] -> [b]
mapx f xs = foldr (\x y -> [f x] ++ y) [] xs

filterx :: (a -> Bool) -> [a] -> [a]
filterx f xs = foldr (\x y -> if (f x) then [x] ++ y else y) [] xs

-- Foldr using recursion
-- foldr :: (a -> b -> b) -> b -> [a] -> b
-- foldr f v [] = v
-- foldr f v (x:xs) = f x (foldr v xs)

-- Generally foldr can be applied on functions that have following behavior.
-- f [] = v
-- f (x:xs) = x # f xs
-- foldr only takes parameters for operator to be applied (#) and value when list is empty (v).


-- Generally foldl can be applied on functions that have following behavior.
-- f [] = v
-- f (x:xs) = f (v # x) xs -- Gives a new accumulator value and then evaluates the end of the list.
-- foldl only takes parameters for operator to be applied (#) and value when list is empty (v).

-- dec2int using foldl
dec2int :: [Int] -> Int
dec2int = foldl (\x y -> x * 10 + y) (0)

-- unfold
unfold p h t x  | p x = []
                | otherwise = h x : unfold p h t (t x)

--chop8 using unfold
chop8_unfold ::[Int] -> [[Int]]
chop8_unfold = unfold (== []) (take 8) (drop 8)

map_unfold :: Eq a => (a -> b) -> [a] -> [b]
map_unfold f = unfold (== []) (\x -> f (head x)) (drop 1)

iterate_unfold :: (Eq a, Num a) => (a -> a) -> a -> [a]
iterate_unfold f = unfold (== 0) (f) (f)

--AltMap
altmap :: (a -> b) -> (a -> b) -> ([a] -> [b])
altmap f1 f2 [] = []
altmap f1 f2 (x:y:[]) = (f1 x): (f2 y) : []
altmap f1 f2 (x:[]) = (f1 x): []
altmap f1 f2 (x:y:xs) = (f1 x): (f2 y) : (altmap f1 f2 xs)

luhnDouble :: Int -> Int
luhnDouble n = f (2 * n)
            where f x = if (x > 9) then x-9 else x

luhn :: [Int] -> Bool
luhn xs = sum (altmap (luhnDouble) (id) xs) `mod` 10 == 0