//package cg.test.entity.ai.goal;
//
//import net.cg.moddedworld.entity.ai.goal.BlockBreakGoal;
//import net.minecraft.Bootstrap;
//import net.minecraft.SharedConstants;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.ai.pathing.MobNavigation;
//import net.minecraft.entity.ai.pathing.Path;
//import net.minecraft.entity.ai.pathing.PathNode;
//import net.minecraft.entity.mob.ZombieEntity;
//import net.minecraft.registry.Registries;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.test.GameTest;
//import net.minecraft.test.TestContext;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Direction;
//import net.minecraft.world.Difficulty;
//import net.minecraft.world.World;
//import org.junit.jupiter.api.*;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class BlockBreakGoalTest {
//
//    @Mock
//    private ZombieEntity mockZombie;
//
//    @Mock
//    private World mockWorld;
//
//    @Mock
//    private MobNavigation mockNavigation;
//
//    @Mock
//    private Path mockPath;
//
//    private BlockBreakGoal blockBreakGoal;
//    private BlockPos zombiePos;
//    private BlockPos wallPos;
//
//    @BeforeAll
//    static void setUpMinecraft() {
//        // Initialize Minecraft registries for testing
//        SharedConstants.createGameVersion();
//        Bootstrap.initialize();
//    }
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Set up basic positions
//        zombiePos = new BlockPos(0, 64, 0);
//        wallPos = new BlockPos(1, 64, 0); // Wall directly in front
//
//        // Configure mock zombie
//        when(mockZombie.getBlockPos()).thenReturn(zombiePos);
//        when(mockZombie.getX()).thenReturn(0.5);
//        when(mockZombie.getY()).thenReturn(64.0);
//        when(mockZombie.getZ()).thenReturn(0.5);
//        when(mockZombie.getHorizontalFacing()).thenReturn(Direction.EAST);
//        when(mockZombie.getWorld()).thenReturn(mockWorld);
//        when(mockZombie.getNavigation()).thenReturn(mockNavigation);
//        when(mockZombie.squaredDistanceTo(anyDouble(), anyDouble(), anyDouble())).thenReturn(1.0);
//
//        // Configure mock world
//        when(mockWorld.getDifficulty()).thenReturn(Difficulty.NORMAL);
//        when(mockWorld.isClient()).thenReturn(false); // Server side
//        when(mockWorld.isChunkLoaded(any(BlockPos.class))).thenReturn(true);
//
//        // Default: air blocks everywhere
//        when(mockWorld.getBlockState(any(BlockPos.class))).thenReturn(Blocks.AIR.getDefaultState());
//
//        blockBreakGoal = new BlockBreakGoal(mockZombie);
//    }
//
//    @Test
//    @DisplayName("Should not start when mob is not colliding horizontally")
//    void shouldNotStartWithoutHorizontalCollision() {
//        // Given: zombie is not stuck
//        when(mockZombie.horizontalCollision).thenReturn(false);
//
//        // When: checking if goal can start
//        boolean canStart = blockBreakGoal.canStart();
//
//        // Then: goal should not start
//        assertFalse(canStart, "Goal should not start when zombie is not stuck");
//    }
//
//    @Test
//    @DisplayName("Should not start on peaceful difficulty")
//    void shouldNotStartOnPeacefulDifficulty() {
//        // Given: zombie is stuck but world is on peaceful
//        when(mockZombie.horizontalCollision).thenReturn(true);
//        when(mockWorld.getDifficulty()).thenReturn(Difficulty.PEACEFUL);
//
//        // When: checking if goal can start
//        boolean canStart = blockBreakGoal.canStart();
//
//        // Then: goal should not start
//        assertFalse(canStart, "Goal should not start on peaceful difficulty");
//    }
//
//    @Test
//    @DisplayName("Should detect breakable block in front of zombie")
//    void shouldDetectBreakableBlockInFront() {
//        // Given: zombie is stuck with a stone wall in front
//        when(mockZombie.horizontalCollision).thenReturn(true);
//        when(mockWorld.getBlockState(wallPos)).thenReturn(Blocks.STONE.getDefaultState());
//        when(mockNavigation.getCurrentPath()).thenReturn(null); // No path, use fallback
//
//        // When: checking if goal can start
//        boolean canStart = blockBreakGoal.canStart();
//
//        // Then: goal should start and target the wall
//        assertTrue(canStart, "Goal should start when breakable block is in front");
//    }
//
//    @Test
//    @DisplayName("Should not target unbreakable blocks")
//    void shouldNotTargetUnbreakableBlocks() {
//        // Given: zombie is stuck with bedrock in front
//        when(mockZombie.horizontalCollision).thenReturn(true);
//        when(mockWorld.getBlockState(wallPos)).thenReturn(Blocks.BEDROCK.getDefaultState());
//        when(mockNavigation.getCurrentPath()).thenReturn(null);
//
//        // When: checking if goal can start
//        boolean canStart = blockBreakGoal.canStart();
//
//        // Then: goal should not start
//        assertFalse(canStart, "Goal should not target bedrock");
//    }
//
//    @Test
//    @DisplayName("Should detect blocks along pathfinding route")
//    void shouldDetectBlocksAlongPath() {
//        // Given: zombie has a path that goes through a wall
//        when(mockZombie.horizontalCollision).thenReturn(true);
//        when(mockNavigation.getCurrentPath()).thenReturn(mockPath);
//        when(mockPath.isFinished()).thenReturn(false);
//        when(mockPath.getCurrentNodeIndex()).thenReturn(0);
//        when(mockPath.getLength()).thenReturn(3);
//
//        // Create path nodes: current -> wall -> destination
//        PathNode node0 = new PathNode(0, 64, 0);  // Current position
//        PathNode node1 = new PathNode(1, 64, 0);  // Wall position
//        PathNode node2 = new PathNode(2, 64, 0);  // Destination
//
//        when(mockPath.getNode(0)).thenReturn(node0);
//        when(mockPath.getNode(1)).thenReturn(node1);
//        when(mockPath.getNode(2)).thenReturn(node2);
//
//        // Place a stone block at the wall position
//        when(mockWorld.getBlockState(new BlockPos(1, 64, 0))).thenReturn(Blocks.STONE.getDefaultState());
//
//        // When: checking if goal can start
//        boolean canStart = blockBreakGoal.canStart();
//
//        // Then: goal should start and target the wall block
//        assertTrue(canStart, "Goal should detect blocks along the path");
//    }
//
//    @Test
//    @DisplayName("Should respect maximum breaking distance")
//    void shouldRespectMaximumBreakingDistance() {
//        // Given: zombie is stuck with a wall that's too far away
//        when(mockZombie.horizontalCollision).thenReturn(true);
//        BlockPos farWallPos = new BlockPos(10, 64, 0); // Far away
//        when(mockWorld.getBlockState(farWallPos)).thenReturn(Blocks.STONE.getDefaultState());
//        when(mockNavigation.getCurrentPath()).thenReturn(null);
//
//        // Mock distance calculation to return a large distance
//        when(mockZombie.squaredDistanceTo(anyDouble(), anyDouble(), anyDouble())).thenReturn(100.0);
//
//        // When: checking if goal can start
//        boolean canStart = blockBreakGoal.canStart();
//
//        // Then: goal should not start due to distance
//        assertFalse(canStart, "Goal should not target blocks that are too far away");
//    }
//
//    @Test
//    @DisplayName("Should check multiple Y levels for blocks")
//    void shouldCheckMultipleYLevels() {
//        // Given: zombie is stuck, no block at ground level but block above
//        when(mockZombie.horizontalCollision).thenReturn(true);
//        when(mockNavigation.getCurrentPath()).thenReturn(null);
//
//        // Ground level is air, but one block up is stone
//        when(mockWorld.getBlockState(new BlockPos(1, 64, 0))).thenReturn(Blocks.AIR.getDefaultState());
//        when(mockWorld.getBlockState(new BlockPos(1, 65, 0))).thenReturn(Blocks.STONE.getDefaultState());
//
//        // When: checking if goal can start
//        boolean canStart = blockBreakGoal.canStart();
//
//        // Then: goal should start and target the block above
//        assertTrue(canStart, "Goal should check multiple Y levels");
//    }
//}
//
//// Integration test class for more realistic scenarios
//@TestMethodOrder(OrderAnnotation.class)
//class BlockBreakGoalIntegrationTest {
//
//    private TestWorld testWorld;
//    private ZombieEntity testZombie;
//    private BlockBreakGoal blockBreakGoal;
//
//    @BeforeAll
//    static void setUpMinecraft() {
//        SharedConstants.createGameVersion();
//        Bootstrap.initialize();
//    }
//
//    @BeforeEach
//    void setUp() {
//        // Create a test world with actual blocks
//        testWorld = new TestWorld();
//        testZombie = testWorld.createZombie(new BlockPos(5, 64, 5));
//        blockBreakGoal = new BlockBreakGoal(testZombie);
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("Integration: Zombie should break through simple wall")
//    void zombieShouldBreakThroughSimpleWall() {
//        // Given: Create a simple wall between zombie and target
//        testWorld.placeWall(new BlockPos(6, 64, 5), Direction.NORTH, 3, Blocks.COBBLESTONE);
//        testWorld.placePlayer(new BlockPos(10, 64, 5)); // Player behind wall
//
//        // Simulate zombie getting stuck
//        testZombie.horizontalCollision = true;
//
//        // When: Goal attempts to start
//        boolean canStart = blockBreakGoal.canStart();
//
//        // Then: Goal should identify wall as breakable
//        assertTrue(canStart, "Zombie should be able to break through cobblestone wall");
//
//        // Simulate breaking process
//        testWorld.simulateGoalExecution(blockBreakGoal, 300); // 15 seconds
//
//        // Verify wall has holes
//        assertTrue(testWorld.hasBreakableHole(new BlockPos(6, 64, 5), new BlockPos(8, 66, 5)),
//                "Wall should have holes after breaking");
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("Integration: Zombie should not break through obsidian")
//    void zombieShouldNotBreakObsidian() {
//        // Given: Create an obsidian wall
//        testWorld.placeWall(new BlockPos(6, 64, 5), Direction.NORTH, 3, Blocks.OBSIDIAN);
//        testZombie.horizontalCollision = true;
//
//        // When: Goal attempts to start
//        boolean canStart = blockBreakGoal.canStart();
//
//        // Then: Goal should not target obsidian
//        assertFalse(canStart, "Zombie should not be able to break obsidian");
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("Integration: Zombie should find alternative path around small obstacles")
//    void zombieShouldPathAroundSmallObstacles() {
//        // Given: Create a small obstacle that can be pathed around
//        testWorld.setBlock(new BlockPos(6, 64, 5), Blocks.STONE);
//        testWorld.placePlayer(new BlockPos(8, 64, 5));
//
//        // Create pathfinding scenario where zombie tries to go around first
//        testWorld.simulatePathfinding(testZombie, new BlockPos(8, 64, 5));
//
//        // When: Check if zombie gets stuck (shouldn't with good pathfinding)
//        boolean needsBreaking = testZombie.horizontalCollision;
//
//        // Then: Zombie should path around rather than break
//        assertFalse(needsBreaking, "Zombie should path around small obstacles when possible");
//    }
//}
//
//// Helper class for creating test scenarios
//class TestWorld {
//    private final World mockWorld;
//    private final BlockState[][][] blocks;
//    private final int size = 32;
//    private final int baseY = 64;
//
//    public TestWorld() {
//        this.mockWorld = mock(World.class);
//        this.blocks = new BlockState[size][size][size];
//
//        // Initialize with air
//        for (int x = 0; x < size; x++) {
//            for (int y = 0; y < size; y++) {
//                for (int z = 0; z < size; z++) {
//                    blocks[x][y][z] = Blocks.AIR.getDefaultState();
//                }
//            }
//        }
//
//        // Set up world mock behavior
//        when(mockWorld.getBlockState(any(BlockPos.class))).thenAnswer(invocation -> {
//            BlockPos pos = invocation.getArgument(0);
//            return getBlock(pos);
//        });
//
//        when(mockWorld.getDifficulty()).thenReturn(Difficulty.NORMAL);
//        when(mockWorld.isClient()).thenReturn(false);
//        when(mockWorld.isChunkLoaded(any(BlockPos.class))).thenReturn(true);
//    }
//
//    public void setBlock(BlockPos pos, net.minecraft.block.Block block) {
//        if (isValidPos(pos)) {
//            blocks[pos.getX()][pos.getY() - baseY][pos.getZ()] = block.getDefaultState();
//        }
//    }
//
//    public BlockState getBlock(BlockPos pos) {
//        if (isValidPos(pos)) {
//            return blocks[pos.getX()][pos.getY() - baseY][pos.getZ()];
//        }
//        return Blocks.AIR.getDefaultState();
//    }
//
//    public void placeWall(BlockPos start, Direction direction, int height, net.minecraft.block.Block block) {
//        for (int y = 0; y < height; y++) {
//            setBlock(start.up(y), block);
//        }
//    }
//
//    public ZombieEntity createZombie(BlockPos pos) {
//        ZombieEntity zombie = mock(ZombieEntity.class);
//        when(zombie.getBlockPos()).thenReturn(pos);
//        when(zombie.getWorld()).thenReturn(mockWorld);
//        when(zombie.getX()).thenReturn(pos.getX() + 0.5);
//        when(zombie.getY()).thenReturn(pos.getY() + 0.0);
//        when(zombie.getZ()).thenReturn(pos.getZ() + 0.5);
//        return zombie;
//    }
//
//    public void placePlayer(BlockPos pos) {
//        // Simulate player presence for pathfinding
//    }
//
//    public void simulatePathfinding(ZombieEntity zombie, BlockPos target) {
//        // Simulate pathfinding behavior
//        MobNavigation nav = mock(MobNavigation.class);
//        when(zombie.getNavigation()).thenReturn(nav);
//
//        // Create a simple path
//        Path path = createPathTo(zombie.getBlockPos(), target);
//        when(nav.getCurrentPath()).thenReturn(path);
//    }
//
//    public void simulateGoalExecution(BlockBreakGoal goal, int ticks) {
//        if (goal.canStart()) {
//            goal.start();
//            for (int i = 0; i < ticks && goal.shouldContinue(); i++) {
//                goal.tick();
//            }
//            goal.stop();
//        }
//    }
//
//    public boolean hasBreakableHole(BlockPos start, BlockPos end) {
//        // Check if there's at least one air block in the area
//        for (int x = start.getX(); x <= end.getX(); x++) {
//            for (int y = start.getY(); y <= end.getY(); y++) {
//                for (int z = start.getZ(); z <= end.getZ(); z++) {
//                    if (getBlock(new BlockPos(x, y, z)).getBlock() == Blocks.AIR) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    private Path createPathTo(BlockPos from, BlockPos to) {
//        // Simple straight-line path for testing
//        Path path = mock(Path.class);
//        when(path.isFinished()).thenReturn(false);
//        when(path.getCurrentNodeIndex()).thenReturn(0);
//        when(path.getLength()).thenReturn(2);
//
//        PathNode fromNode = new PathNode(from.getX(), from.getY(), from.getZ());
//        PathNode toNode = new PathNode(to.getX(), to.getY(), to.getZ());
//
//        when(path.getNode(0)).thenReturn(fromNode);
//        when(path.getNode(1)).thenReturn(toNode);
//
//        return path;
//    }
//
//    private boolean isValidPos(BlockPos pos) {
//        return pos.getX() >= 0 && pos.getX() < size &&
//                pos.getY() >= baseY && pos.getY() < baseY + size &&
//                pos.getZ() >= 0 && pos.getZ() < size;
//    }
//}