luhnDouble :: Int -> Int
luhnDouble n = f (2 * n)
            where f x = if (x > 9) then x-9 else x


luhn :: Int -> Int -> Int -> Int -> Bool
luhn a b c d = ((luhnDouble a) + b + (luhnDouble c) + d) `mod` 10 == 0