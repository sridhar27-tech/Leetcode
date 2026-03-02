class Solution:
    def findDifference(self, nums1: List[int], nums2: List[int]) -> List[List[int]]:
        set1 = set(nums1)
        set2 = set(nums2)
        set3 = set()
        set4 = set()

        for item in set1:
            if item not in set2:
                set3.add(item)
        for item in set2:
            if item not in set1:
                set4.add(item)    

        set3 = list(set3)
        set4 = list(set4)

        return [set3, set4]

                
        