class Solution:
    def exist(self, board: List[List[str]], word: str) -> bool:
        rows, cols = len(board), len(board[0])
        
        def backtrack(i, j, k):
            if k == len(word):
                return True
            
            if i < 0 or i >= rows or j < 0 or j >= cols or board[i][j] != word[k]:
                return False
            
            temp = board[i][j]
            board[i][j] = "#"
            
            # 4. éå½æ¢ç´¢åä¸ªæ¹å
            found = (backtrack(i + 1, j, k + 1) or 
                     backtrack(i - 1, j, k + 1) or
                     backtrack(i, j + 1, k + 1) or
                     backtrack(i, j - 1, k + 1))
            
            board[i][j] = temp
            
            return found

        for r in range(rows):
            for c in range(cols):
                if backtrack(r, c, 0):
                    return True
                    
        return False